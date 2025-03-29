<script setup>
import { ref, defineProps } from 'vue'

const props = defineProps({
  id: String, 
  labelName: String,
  options: Array,
  modelValue: String 
})

const emit = defineEmits(['update:modelValue']) 

// Väärtuse muutmisel kajastab seda parent componentis.
const handleChange = (event) => {
  emit('update:modelValue', event.target.value)
}
</script>

<template>
  <div>
    <label :for="labelName" class="block text-left text-sm text-gray-500 dark:text-gray-300">
      {{ labelName }}
    </label>

    <select 
      :id="id"
      :value="modelValue"
      @change="handleChange"
      class="block mt-2 w-full placeholder-gray-400/70 dark:placeholder-gray-500 
             rounded-lg border border-gray-200 bg-white px-5 py-2.5 
             text-gray-700 focus:border-blue-400 focus:outline-none 
             focus:ring focus:ring-blue-300 focus:ring-opacity-40 
             dark:border-gray-600 dark:bg-gray-900 dark:text-gray-300 
             dark:focus:border-blue-300">
      <option value="">Kõik</option>
      <option v-for="(option, index) in options" :key="index" :value="option">
        {{ option }}
      </option>
    </select>
  </div>
</template>
