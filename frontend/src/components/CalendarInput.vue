<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  label: String,
  modelValue: String
})

const emit = defineEmits(['update:modelValue'])
// Väärtuse muutmisel kajastab uut väärtust parent componentis.
const handleDateChange = (event => {
    console.log(event.target.value)
    emit('update:modelValue', event.target.value)
})
</script>

<template>
  <div>
    <label for={{label}} class="block text-sm text-left text-gray-500 dark:text-gray-300">{{ label }}</label>
    <input type="date"
           :value="modelValue"
           @change="handleDateChange"
           class="block  mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 rounded-lg border border-gray-200 bg-white px-5 py-2.5 text-gray-700 focus:border-blue-400 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40 dark:border-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:focus:border-blue-300" />
  </div>
</template>

<style scoped>

</style>