import {writable} from 'svelte/store';

const userStore = writable([]);

const customUserStore = {
    subscribe: userStore.subscribe,
    setUsers: (userArray) => {
        userStore.set(userArray);
    },
    addUser: userData => {
        userStore.update(items => {
            return [userData, ...items];
        });
    },
    updateUser: (id, userData) => {
        userStore.update(items => {
            const index = items.findIndex(user => user.id === id);
            const updatedUserArray = [...items];
            updatedUserArray[index] = userData;
            return updatedUserArray;
        });
    },
    deleteUser: (id) => {
        userStore.update(items => {
            return items.filter(user => user.id !== id);
        });
    }
};

export default customUserStore;
