<script context="module">
    export function preload(page) {
        const userId = page.params.id;

        return this.fetch('http://localhost:8080/user/' + userId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
                .then(res => {
                    if (!res.ok) {
                        throw new Error('Failed!');
                    }
                    return res.json();
                })
                .then(data => {
                    return {selectedUser: data};
                })
                .catch(err => {
                    console.log(err);
                    this.error(404, 'Could not fetch meetup!');
                });
    }
</script>

<script>
    import {createEventDispatcher} from 'svelte';
    import Button from '../components/ui/Button.svelte';

    export let selectedUser;

    const dispatch = createEventDispatcher();
</script>

<style>
    section {
        margin-top: 4rem;
    }

    .content {
        text-align: center;
        width: 80%;
        margin: auto;
    }

    h1 {
        font-size: 2rem;
        font-family: 'Roboto Slab', sans-serif;
        margin: 0.5rem 0;
    }

    h2 {
        font-size: 1.25rem;
        color: #6b6b6b;
    }

    p {
        font-size: 1.5rem;
    }
</style>

<section>
    <div class="content">
        <h1>{selectedUser.username}</h1>
        <h2>{selectedUser.email}</h2>
        <p>Enabled: {selectedUser.enabled ? 'yes' : 'no'}</p>
        <p>Locked: {selectedUser.locked ? 'yes' : 'no'}</p>
        <Button href="mailto:{selectedUser.email}">Contact</Button>
        <Button mode="outline" href="/">Close</Button>
    </div>
</section>


