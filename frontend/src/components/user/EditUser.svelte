<script>
    import {createEventDispatcher} from 'svelte';

    import Button from '../ui/Button.svelte';
    import Modal from '../ui/Modal.svelte';
    import TextInput from '../ui/TextInput.svelte';

    import {isEmpty, isValidEmail} from '../../util/validation';

    import userStore from '../../store/UserStore';

    const dispatch = createEventDispatcher();

    export let id = null;

    let username = '';
    let email = '';
    let enabled = false;
    let locked = false;

    if (id) {
        fetch('http://backend:8080/user/' + id, {
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
                    username = data.username;
                    email = data.email;
                    enabled = data.enabled;
                    locked = data.locked;
                })
                .catch(err => {
                    console.log(err);
                });
    }

    $: usernameValid = !isEmpty(username);
    $: emailValid = isValidEmail(email);

    $: formIsValid = usernameValid && emailValid;

    function cancel() {
        dispatch('cancel');
    }

    function submitForm() {
        const userData = {
            id: id,
            username: username,
            email: email,
            enabled: enabled,
            locked: locked
        };

        if (id) {
            fetch('http://backend:8080/user/' + id, {
                method: 'PUT',
                body: JSON.stringify(userData),
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
                        userStore.updateUser(id, data);
                    })
                    .catch(err => {
                        console.log(err);
                    });
        } else {
            fetch('http://backend:8080/user/', {
                method: 'POST',
                body: JSON.stringify(userData),
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
                        userStore.addUser(userData);
                    })
                    .catch(err => {
                        console.log(err);
                    });
        }
        dispatch('save');
    }

    function deleteUser() {
        fetch('http://backend:8080/user/' + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
                .then(res => {
                    if (!res.ok) {
                        throw new Error('Failed!');
                    }
                    userStore.deleteUser(id);
                })
                .catch(err => {
                    console.log(err);
                });
        dispatch('save');
    }
</script>

<style>
    form {
        width: 100%;
    }
</style>

<Modal title="Edit user" on:cancel>
    <form>
        <TextInput
                id="username"
                label="Username"
                valid={usernameValid}
                validityMessage="Please enter a valid username."
                value={username}
                on:input={(event) => username = event.target.value}
        />
        <TextInput
                id="email"
                label="User email"
                valid={emailValid}
                validityMessage="Please enter a valid email."
                type="email"
                value={email}
                on:input={(event) => email = event.target.value}
        />
        <label>
            <input type="checkbox" bind:checked={enabled}/>
            Enabled
        </label>
        <label>
            <input type="checkbox" bind:checked={locked}/>
            Locked
        </label>
    </form>
    <div slot="footer">
        <Button mode="outline" on:click={cancel}>Cancel</Button>
        <Button on:click={submitForm} disabled={!formIsValid}>Save</Button>
        {#if id}
            <Button on:click={deleteUser}>Delete</Button>
        {/if}
    </div>
</Modal>
