<script>
    import {createEventDispatcher} from 'svelte';

    import Button from '../ui/Button.svelte';

    export let id;
    export let username;
    export let email;
    export let enabled;
    export let locked;

    const dispatch = createEventDispatcher();
</script>

<style>
    article {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
        border-radius: 5px;
        background: white;
        margin: 1rem;
    }

    header,
    footer {
        padding: 1rem;
    }

    h1 {
        font-size: 1.25rem;
        margin: 0.5rem 0;
        font-family: "Roboto Slab", sans-serif;
    }

    p {
        font-size: 1.25rem;
        margin: 0;
    }
</style>

<article>
    <header>
        <h1>
            {username}
        </h1>
        <p>{email}</p>
        <p>Enabled: {enabled ? 'yes' : 'no'}</p>
        <p>Locked: {locked ? 'yes' : 'no'}</p>
    </header>
    <footer>
        <Button mode="outline" on:click={() => dispatch('edit',id)}>Edit</Button>
        <Button href={'/' + id}>Show Details</Button>
    </footer>
</article>
