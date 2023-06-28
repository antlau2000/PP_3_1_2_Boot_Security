const form_new = document.getElementById('formForNewUser');
const role_new = document.querySelector('#roles').selectedOptions;

form_new.addEventListener('submit', addNewUser);

async function loadRolesForNewUser() {
    const urlDataRoles = 'api/roles/';
    let newUserPageRoles = await fetch(urlDataRoles);
    if (newUserPageRoles.ok) {
        const roles = await newUserPageRoles.json();
        const selectElement = document.getElementById('roles');
        selectElement.innerHTML = '';
        roles.forEach(role => {
            const optionElement = document.createElement('option');
            optionElement.value = role.id;
            optionElement.textContent = role.name;
            selectElement.appendChild(optionElement);
        });
    }
}

async function addNewUser(event) {
    event.preventDefault();
    const urlNew = 'api/users';
    let listOfRole = [];
    for (let i = 0; i < role_new.length; i++) {
        listOfRole.push({
            id:role_new[i].value,
            name:role_new[i].name,
            users:role_new[i].users,
            authority:role_new[i].authority
        });
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_new.name.value,
            surname: form_new.surname.value,
            age: form_new.age.value,
            username: form_new.username.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }
    await fetch(urlNew,method).then(() => {
        form_new.reset();
        getAdminPage();
        document.getElementById("user_table-tab").click();
    });
}




