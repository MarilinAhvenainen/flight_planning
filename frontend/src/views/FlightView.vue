<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';


const props = defineProps({
  id: {
    type: String,
    required: true
  },
  seatNumber: {
    type: Number,
    default: () => Number(useRoute().query.seatNumber) || 1
  }
});

const emit = defineEmits(['seat-selection']);
const router = useRouter(); 

const seats = ref([]);
const groupedSeats = ref([]);
const selectedSeats = ref([]);

const filters = ref({
  windowSeat: false,
  legRoom: false,
  nearExit: false
});

// Grupeerib istekohad kahte tulpa (6 istet rea kohta)
const groupSeats = () => {
  const rows = [];
  for (let i = 0; i < seats.value.length; i += 6) {
    const rowSeats = seats.value.slice(i, i + 6);
    rows.push({
      leftSeats: rowSeats.slice(0, 3),
      rightSeats: rowSeats.slice(3, 6)
    });
  }
  groupedSeats.value = rows;
};

/*Istekohad vastavalt filtritele*/
const seatMatchesFilters = (seat) => {
  const { windowSeat, legRoom, nearExit } = filters.value;
  
  // kui filtreid ei ole siis tagastab false.
  if (!windowSeat && !legRoom && !nearExit) return false;
  
  return (
    (windowSeat ? seat.type === 'A'|| seat.type ==='F' : true) &&   
    (legRoom ? seat.legRoom : true) &&
    (nearExit ? seat.nearExit : true)
  );
};

/*Claude genereeritud. Pakub kõrvuti olevaid istekohti esmasel lehele saabumisel
  Aga ei arvesta näiteks sellega kui Rida1 F siis rida2 A ei ole kõrval. Ehk siis ei tööta korrektselt.
*/
const findConsecutiveSeats = () => {
  // Vabad istekohad
  const availableSeats = seats.value.filter(seat => !seat.isBooked);
  
  // Kui vabu kohti ei ole piisavalt
  if (availableSeats.length < props.seatNumber) return [];
  
  // Leia kõrvuti istekohad
  for (let i = 0; i <= availableSeats.length - props.seatNumber; i++) {
    const consecutiveSeats = availableSeats.slice(i, i + props.seatNumber);
    
    const areConsecutive = consecutiveSeats.every((seat, index) => {
      if (index === 0) return true;
      return parseInt(seat.number) === parseInt(consecutiveSeats[index-1].number) + 1;
    });
    
    if (areConsecutive) {
      return consecutiveSeats;
    }
  }
  
  // Kui kõrvitu kohti ei ole siis n esimest kohta. 
  return availableSeats.slice(0, props.seatNumber);
};

// Kinnita valitud istekoht (Booking)
const confirmSelection = async () => {
    if(selectedSeats.value.length == 0){
      alert('Palun vali istekoht.'); // kui istekoht on valimata
    }else{
      try {
        //POST request 
        for (let seat of selectedSeats.value) {
          const response = await axios.post(`/api/flights/${props.id}/seats/${seat.id}`);
        }
        
        alert('Istekoht on edukalt broneeritud!');
        redirectToMain(); // suunab tagasi avalehele

      } catch (error) {
          console.error('Error booking seats:', error.response ? error.response.data : error.message);
          alert('Tekkis viga istekoha broneerimisel.');
      }
    }
    
};

/*Get request - vastava lennu istekohtade saamiseks*/
const fetchData = async () => {
    try {
        const response = await axios.get(`api/flights/${props.id}/seats`);
        console.log('API Response:', response.data)
        seats.value = response.data.sort((a, b) => parseInt(a.number) - parseInt(b.number));
        groupSeats();
        
        // Automaatselt valib istekohad
        selectedSeats.value = findConsecutiveSeats();
        emit('seat-selection', selectedSeats.value);

    } catch (error) {
        console.error('Full Error:', error.response ? error.response.data : error.message);
    }
};

/*Claude poolt: Istekoha valimine/klikkimine*/
const selectSeat = (seat) => {
  // Broneeritud istekohad ei ole saadaval. 
  if (seat.isBooked) return;

  // Kui juba valitud siis deselect
  const seatIndex = selectedSeats.value.findIndex(s => s.id === seat.id);

  if (seatIndex !== -1) {
    selectedSeats.value.splice(seatIndex, 1);
  } else {
    // Saab valida ainult reisijate arvu jagu istekohti
    if (selectedSeats.value.length >= props.seatNumber) {
      // Eemaldatakse esimene kui rohkem vajutada
      selectedSeats.value.shift();
    }
    
    // Lisa valitud isekoht listi
    selectedSeats.value.push(seat);
  }
  emit('seat-selection', selectedSeats.value);
};

const isSeatSelected = (seat) => {
  return selectedSeats.value.some(s => s.id === seat.id);
};

/*Avalehele suunamine*/
const redirectToMain = () => { 
    router.push("/"); 
};

onMounted(() => {
  fetchData();
});
</script>

<template>
    <header class="md:bg-gray-100 dark:bg-gray-800 shadow-lg p-4">
        <h2 class="text-left mb-4 text-gray-900 dark:text-white">Lennu number: {{ props.id }}</h2>
        <p class="text-left mb-4 text-gray-600 dark:text-gray-400">Vali {{ seatNumber }} istekoht(a)</p>
        <div v-if="selectedSeats.length > 0" class="text-left mb-8">
          <p class="text-gray-800 dark:text-white">Valitud istekohad: 
            <span v-for="(seat, index) in selectedSeats" :key="seat.id" class="mx-1 text-blue-600 dark:text-blue-400">
              {{ seat.number }}{{ index < selectedSeats.length - 1 ? ', ' : '' }}
            </span>
          </p>
        </div>
        <div class="flex items-center gap-x-3">
                                        <input v-model="filters.windowSeat" type="checkbox" class="text-blue-500 border-gray-300 rounded dark:bg-gray-900 dark:ring-offset-gray-900 dark:border-gray-700">
                                        <span>akna all</span>
        </div>
        <div class="flex items-center gap-x-3">
                                        <input v-model="filters.legRoom" type="checkbox" class="text-blue-500 border-gray-300 rounded dark:bg-gray-900 dark:ring-offset-gray-900 dark:border-gray-700">
                                        <span>rohkem jalaruumi</span>
        </div>
        <div class="flex items-center gap-x-3" >
                                        <input type="checkbox" v-model="filters.nearExit" class="text-blue-500 border-gray-300 rounded dark:bg-gray-900 dark:ring-offset-gray-900 dark:border-gray-700">
                                        <span>lähedal väljapääsule</span>
        </div>
        <div class="flex gap-20 justify-end mt-4">
            <button 
                type="submit" 
                @click="confirmSelection"
                class="px-6 py-2 font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-600 rounded-lg hover:bg-blue-500 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-80">
                Kinnita
            </button>
        </div> 
    </header>
    <section class="max-w-4xl mx-auto">
      <div class="flex justify-center items-center mb-4">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=chair" />
      </div>
      <!-- Seats Grid Container -->
      <div class="grid grid-cols-2 gap-4">
        <!-- Left Column -->
        <div class="space-y-4">
          <div v-for="(row, rowIndex) in groupedSeats" :key="rowIndex" class="flex gap-4">
            <div 
              v-for="seat in row.leftSeats" 
              :key="seat.id" 
              @click="selectSeat(seat)"
              class="flex flex-col items-center cursor-pointer transform transition-all duration-200 hover:scale-105"
              :class="{ 'opacity-50 cursor-not-allowed': seat.isBooked }"
            >
              <!-- Colored Chair Icon Using Tailwind Colors -->
              <span 
                class="material-symbols-outlined text-5xl"
                :class="[
                  seat.isBooked ? 'text-gray-500 dark:text-gray-400' : 
                  isSeatSelected(seat) ? 'text-orange-600 dark:text-orange-500 hover:text-yellow-500' : 
                  seatMatchesFilters(seat) ? 'text-pink-500 dark:text-pink-500 hover:text-pink-400':
                  'text-blue-700 dark:text-blue-600 hover:text-blue-400'
                ]"
              >
                chair
              </span>
  
              <div class="text-center mt-2 text-gray-800 dark:text-white">
                <p class="font-bold">{{ seat.number }}</p>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                  {{ seat.type }} - 
                  <span :class="seat.isBooked ? 'text-gray-600 dark:text-gray-400' : 'text-blue-700 dark:text-blue-500 hover:text-blue-400'">
                    {{ seat.isBooked ? 'Kinni' : 'Vaba' }}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Right Column -->
        <div class="space-y-4">
          <div v-for="(row, rowIndex) in groupedSeats" :key="rowIndex" class="flex gap-4">
            <div 
              v-for="seat in row.rightSeats" 
              :key="seat.id" 
              @click="selectSeat(seat)"
              class="flex flex-col items-center cursor-pointer transform transition-all duration-200 hover:scale-105"
              :class="{ 'opacity-50 cursor-not-allowed': seat.isBooked }"
            >
              <!-- Colored Chair Icon Using Tailwind Colors -->
              <span 
                class="material-symbols-outlined text-5xl"
                :class="[
                  seat.isBooked ? 'text-gray-500 dark:text-gray-400' : 
                  isSeatSelected(seat) ? 'text-orange-600 dark:text-orange-500 hover:text-yellow-500' : 
                  seatMatchesFilters(seat) ? 'text-pink-500 dark:text-pink-500 hover:text-pink-400':
                  'text-blue-700 dark:text-blue-600 hover:text-blue-400'
                ]"
              >
                chair
              </span>
  
              <div class="text-center mt-2 text-gray-800 dark:text-white">
                <p class="font-bold"> {{ seat.number }}</p>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                  {{ seat.type }} - 
                  <span :class="seat.isBooked ? 'text-gray-600 dark:text-gray-400' : 'text-blue-700 dark:text-blue-500 hover:text-blue-400'">
                    {{ seat.isBooked ? 'Kinni' : 'Vaba' }}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </template>
  
  
  

<style scoped>
.material-symbols-outlined {
  font-size: 48px;
  font-variation-settings:
  'FILL' 0,
  'wght' 200,
  'GRAD' -25,
  'opsz' 48
}
</style>