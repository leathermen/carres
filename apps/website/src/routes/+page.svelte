<script lang="ts">
  import { env } from '$env/dynamic/public';
  import { construct_svelte_component } from 'svelte/internal';

  export let status: string | null = null;

  const fetchStatus = async () => {
    status = await (await fetch(`${env.PUBLIC_API_URL}/open/date`)).text();
  };
</script>

<button
  class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
  on:click={fetchStatus}
>
  Button
</button>

<pre>{status}</pre>
