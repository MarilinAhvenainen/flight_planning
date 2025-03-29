<script setup>
import { useRouter } from 'vue-router';

const props = defineProps({
  flights: {
    type: Array,
    default: () => []
  },
  travellerCount: {
    type: Number,
    required: true,
  }
})
const router = useRouter(); 

/* Suunab istekohtade valimise lehele */
const redirectToBooking = (flight) => {
    router.push({ path: `/flights/${flight.id}`, params: { id: flight.id}, query: { seatNumber: props.travellerCount } }); 
};
</script>

<template>
    <section class="container px-4 mx-auto">
        <div class="flex items-center gap-x-3">
            <span 
                v-if="flights.length" 
                class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full dark:bg-gray-800 dark:text-blue-400">
                {{ flights.length }} {{ flights.length === 1 ? 'tulemus' : 'tulemust' }}
            </span>
        </div>

      <div class="flex flex-col mt-6">
          <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
              <div class="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
                  <div class="overflow-hidden border border-gray-200 dark:border-gray-700 md:rounded-lg">
                      <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                          <thead class="bg-gray-50 dark:bg-gray-800">
                              <tr>

                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">Alguspunkt</th>
                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">Sihtpunkt</th>
                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">kuupäev</th>
                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">kellaaeg</th>
                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400">Hind</th>
                                  <th scope="col" class="px-4 uppercase py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400"> 
                                    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,200,0,-25&icon_names=airplane_ticket" />
                                    <span class="material-symbols-outlined">airplane_ticket</span>
                                  </th>
                              </tr>
                          </thead>
                          <tbody v-if="flights.length" class="bg-white divide-y divide-gray-200 dark:divide-gray-700 dark:bg-gray-900">
                              <tr v-for="flight in flights" :key="flight.id">
                                  <td class="px-4 py-4 text-left text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">{{flight.origin}}</td>
                                  <td class="px-4 py-4 text-left text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">{{flight.destination}}</td>
                                  <td class="px-4 py-4 text-left text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">{{flight.date}}</td>
                                  <td class="px-4 py-4 text-left text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">{{ flight.time }}</td>
                                  <td class="px-4 py-4 text-left text-sm text-gray-500 dark:text-gray-300 whitespace-nowrap">{{flight.price}}€</td>
                                  <td class="px-4 py-4 text-left text-sm whitespace-nowrap">
                                    <div class="flex items-center gap-x-2">
                                    <button @click="redirectToBooking(flight)" class="text-gray-500 transition-colors duration-200 dark:hover:text-yellow-500 dark:text-gray-300 hover:text-yellow-500 focus:outline-none flex items-center">
                                        <p class="uppercase mr-2">ostma</p> <!-- Added margin-right for spacing -->
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                                        
                                        <path stroke-linecap="round" stroke-linejoin="round" d="m12.75 15 3-3m0 0-3-3m3 3h-7.5M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                                        </svg>
                                    </button>
                                    </div>
                                  </td>
                              </tr>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
      </div>
  </section>
</template>

<style scoped>

</style>