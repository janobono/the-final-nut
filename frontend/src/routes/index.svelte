<script context="module">
    export function preload(page) {
        return this.fetch('http://backend:8080/user/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
                .then(res => {
                    if (!res.ok) {
                        throw new Error('Fetching users failed, please try again later!');
                    }
                    return res.json();
                })
                .then(data => {
                    return {userList: data.reverse()};
                })
                .catch(err => {
                    console.log(err);
                    this.error(500, 'Could not fetch users!');
                });
    }
</script>

<script>
    import {createEventDispatcher, onMount, onDestroy} from 'svelte';
    import {scale} from 'svelte/transition';
    import {flip} from 'svelte/animate';

    import EditUser from '../components/user/EditUser.svelte';
    import UserItem from '../components/user/UserItem.svelte';

    import Button from '../components/ui/Button.svelte';
    import LoadingSpinner from '../components/ui/LoadingSpinner.svelte';

    import userStore from '../store/UserStore';

    export let userList;

    let loadedUsers = [];
    let unsubscribe;
    let editMode = null;
    let editedId = null;

    const dispatch = createEventDispatcher();

    onMount(() => {
        unsubscribe = userStore.subscribe(items => {
            loadedUsers = items;
        });
        userStore.setUsers(userList);
    });

    onDestroy(() => {
        if (unsubscribe) {
            unsubscribe();
        }
    });

    function startAdd() {
        editMode = 'edit';
        editedId = null;
    }

    function startEdit(event) {
        editMode = 'edit';
        editedId = event.detail;
    }

    function cancelEdit() {
        editMode = null;
        editedId = null;
    }

    function savedUser() {
        editMode = null;
        editedId = null;
    }
</script>

<style>
    #users {
        width: 100%;
        display: grid;
        grid-template-columns: 1fr;
        grid-gap: 1rem;
    }

    #user-controls {
        margin: 1rem;
        display: flex;
        justify-content: space-between;
    }

    #no-users {
        margin: 1rem;
    }

    @media (min-width: 768px) {
        #users {
            grid-template-columns: repeat(2, 1fr);
        }
    }
</style>

<svelte:head>
    <title>The final nut</title>
</svelte:head>

{#if editMode === 'edit'}
    <EditUser id={editedId} on:save={savedUser} on:cancel={cancelEdit}/>
{/if}
<section id="user-controls">
    <Button on:click={startAdd}>New User</Button>
</section>
{#if loadedUsers.length === 0}
    <p id="no-users">No users found, you can start adding some.</p>
{/if}
<section id="users">
    {#each loadedUsers as user, index (user.id)}
        <div transition:scale animate:flip={{duration: 300}}>
            <UserItem {...user} on:edit={startEdit}/>
        </div>
    {/each}
</section>
