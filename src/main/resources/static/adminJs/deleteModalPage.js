const id_del = document.getElementById('id_del');
const name_del = document.getElementById('name_del');
const surname_del = document.getElementById('surname_del');
const age_del = document.getElementById('age_del');
const username_del = document.getElementById('username_del');
const role_del = document.getElementById("delete_role")
const deleteModal = document.getElementById("deleteModal");
const closeDeleteButton = document.getElementById("closeDelete")
const bsDeleteModal = new bootstrap.Modal(deleteModal);

async function deleteModalData(id) {
    const urlForDel = 'api/users/' + id;
    let usersPageDel = await fetch(urlForDel);
    if (usersPageDel.ok) {
        await usersPageDel.json().then(user => {
            id_del.value = `${user.id}`;
            name_del.value = `${user.name}`;
            surname_del.value = `${user.surname}`;
            age_del.value = `${user.age}`;
            username_del.value = `${user.username}`;
            role_del.value = user.roles.map(r => r.name).join(", ");
        })
        bsDeleteModal.show();
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}

async function deleteUser() {
    let urlDel = 'api/users/' + id_del.value;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeDeleteButton.click();
        getAdminPage();
    })
}