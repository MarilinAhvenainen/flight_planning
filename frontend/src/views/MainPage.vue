<script setup>
import FormComponent from '../components/FormComponent.vue';
import TableComponent from '../components/TableComponent.vue';
import { ref, onMounted } from 'vue';
import axios from 'axios'
const flights = ref([]); 
const allFlights = ref([]); // kasutatakse alguspunkt ja sihtpunkt valikute kuvamiseks
const travellerCount = ref(1);

// Lehe avamisel kõikide lendude kuvamine
const fetchData = async () => {
    try {
        const response = await axios.get("/api/flights/all");
        console.log('API Response:', response.data);
        flights.value = response.data;
        allFlights.value = response.data;
    } catch (error) {
        console.error('Full Error:', error.response ? error.response.data : error.message);
    }
};

onMounted(() => {
  fetchData();
});

/* Uuendab filtreeritud lendude infot */
const updateFlights = (newFlights) => {
  flights.value = newFlights.flights;
  travellerCount.value = newFlights.travellerCount;
};
</script>

<template>
  <header class="md:bg-gray-100 dark:bg-gray-800 shadow-lg">
    <section class="flex flex-col max-w-4xl mx-auto overflow-hidden rounded-lg md:flex-row md:h-90">
      <div class="md:flex md:items-center md:justify-center md:w-1/2 md:bg-gray-100 md:dark:bg-gray-800">
        <div class="px-6 py-6 md:px-8 md:py-0">
          <h2 class="text-lg font-bold text-gray-900 dark:text-white md:text-gray-800">
            Lennupiletite <span class="text-blue-600 dark:text-blue-400 md:text-blue-600">Otsing</span>
          </h2>
          <p class="mt-2 text-sm text-gray-600 dark:text-gray-400 md:text-gray-400">
            Vali algpunkt, sihtpunkt ning kuupäev.
          </p>
        </div>
      </div>
      <div class="flex items-center justify-center pb-6 md:py-0 md:w-3/4">
        <FormComponent :allFlights="allFlights" @updateFlights="updateFlights" />
      </div>
    </section>  
  </header>
  <section class="flex  mt-8">
    <TableComponent :flights="flights" :travellerCount="travellerCount"></TableComponent>
  </section>
</template>

<style scoped>
</style>