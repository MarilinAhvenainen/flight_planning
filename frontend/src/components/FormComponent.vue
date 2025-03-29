<script setup>

import { ref, defineEmits } from 'vue';
import FormInput from './FormInput.vue';
import CalendarInput from './CalendarInput.vue';
import axios from 'axios';

const props = defineProps({
  allFlights: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['updateFlights']);
const flights = ref([]);

//Otsingu parameetrid
const searchParams = ref({
    origin: '',
    destination: '',
    departureDate: '',
    travellerCount:1,
    maxPrice: null,
});

// Tagastab unikaalsete sihtpunktide nimekirja
const getUniqueDestinations = () => {
    return [...new Set(props.allFlights.map(flight => flight.destination))];
};

// Tagastab unikaalsete alguspunktide nimekirja
const getUniqueOrigins = () => {
    return [...new Set(props.allFlights.map(flight => flight.origin))];
};

/*Get request istekohtade arvu jaoks.
Tagastab: boolean (kas istekohti jagub valitud arvule reisijatele.)
*/
const hasEnoughSeats = async (flight) => {
    try{
        const response = await axios.get(`api/flights/${flight.id}/seats`);
        console.log('API Response:', response.data.length)
        const filteredSeats =  response.data.filter(seat => { return seat.isBooked == false})
        const seatCount = filteredSeats.length;
        return seatCount>=searchParams.value.travellerCount;
    }catch (error) {
        console.error('Error fetching seat count:', error);
    }

}

/*Filtrite põhjal lendude filtreerimine. 
Algpunkti ja/või sihtpunki järgi filtreerimine toimub backendi poolel.
Kuupäevade,hinna, istekohtade arvu järgi filtreerimine toimub frontendis.
*/
const fetchData = async () => {
    try {
        // Alguspunkt/Sihtpunkt
        console.log('Search Params:', searchParams.value);
        const response = await axios.get('/api/flights', {
            params: {
                origin: searchParams.value.origin,
                destination: searchParams.value.destination
            }
        });
        let filteredFlights = response.data;
        
        //Kuupäev
        if(searchParams.value.departureDate){
            const date = new Date(searchParams.value.departureDate).setHours(0, 0, 0, 0);; // valitud kuupäev
            filteredFlights = filteredFlights.filter(flight => {
                const d = new Date(flight.date).setHours(0, 0, 0, 0);
                return date === d;
            })
        }
        // maksimum hind
        if(searchParams.value.maxPrice){
            filteredFlights = filteredFlights.filter(flight => {
                return flight.price <= searchParams.value.maxPrice;
            })
        }
        // istekohtade arv
        const availabilityChecks = filteredFlights.map(flight => 
            hasEnoughSeats(flight).then(isAvailable => 
                isAvailable ? flight : null
            )
        );
        const results = await Promise.all(availabilityChecks);
        const flightsWithEnoughSeats = results.filter(flight => flight !== null);

        // Tulemuste kuvamine parent componentile.
        emit('updateFlights', { 
            flights: flightsWithEnoughSeats, 
            travellerCount: searchParams.value.travellerCount,
            });
    } catch (error) {
        console.error('Error:', error.response ? error.response.data : error.message);
    }
};
</script>

<template>
    <form @submit.prevent="fetchData">
        <div class="grid grid-cols-2 gap-6 w-full">
            <FormInput 
                id="origin"
                v-model="searchParams.origin"
                labelName="Alguspunkt"
                :options="getUniqueOrigins()" 
                class="flex-1">
            </FormInput>
            <FormInput 
                id="destination"
                v-model="searchParams.destination"
                labelName="Sihtpunkt"
                :options="getUniqueDestinations()" 
                class="flex-1">
            </FormInput>
        </div>
        <div class="grid grid-cols-2 gap-6 mt-4 w-full">
            <CalendarInput 
                v-model="searchParams.departureDate"
                label="Kuupäev" 
                class="flex-1">
            </CalendarInput>
            <div>
                <label for="seatCount" class="block text-left text-sm text-gray-500 dark:text-gray-300">
                Reisijate arv
                </label>
                <input v-model="searchParams.travellerCount" class="block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-gray-200 bg-white px-3 py-2.5 text-gray-700 focus:border-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40 dark:border-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:focus:border-blue-300" type="number" name="seatCount" placeholder="istekohtade arv" aria-label="Sisesta istekohtade arv">
            </div>
            
        </div>
        <div class="grid grid-cols-2 gap-6 mt-4 w-full">
            <div>
                <label for="maxPrice" class="block text-left text-sm text-gray-500 dark:text-gray-300">
                Maksimum hind
                </label>
                <input v-model="searchParams.maxPrice" class="block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-gray-200 bg-white px-3 py-2.5 text-gray-700 focus:border-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40 dark:border-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:focus:border-blue-300" type="number" name="seatCount" placeholder="200">
            </div>
            
        </div>

        <div class="flex gap-20 justify-end mt-4">
            <button 
                type="submit" 
                class="px-6 py-2 font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-600 rounded-lg hover:bg-blue-500 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-80">
                Otsi
            </button>
        </div> 
    </form>
</template>