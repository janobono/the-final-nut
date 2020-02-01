<script>
    import Header from '../components/ui/Header.svelte';

    export let segment;
</script>

<style>
    main {
        margin-top: 5rem;
    }
</style>

<Header/>

<main>
    <slot></slot>
</main>
