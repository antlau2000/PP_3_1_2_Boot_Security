const users = '/api/admin/users';
const auth = "/api/user/auth"

const currentUser = fetch(auth).then(response => response.json())
currentUser.then(user => {
        let roles = ''
        user.roles.forEach(role => {
            roles += ' '
            roles += role.name
        })
        document.getElementById("navbar-username").innerHTML = user.username
        document.getElementById("navbar-roles").innerHTML = roles
    }
)

async function getAdminPage() {
    let page = await fetch(users);

    if(page.ok) {
        let listAllUser = await  page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}
function loadTableData(listAllUser) {
    const tableBody = document.getElementById('usersTableBody');
    let dataHtml = '';
    for (let user of listAllUser) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name.toString())
        }
        dataHtml +=
`<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.name}</td>
    <td>${user.surname}</td>
    <td>${user.age}</td>
    <td>${roles}</td>
    <td>
        <button type="button" class="btn btn-primary" data-bs-toogle="modal"
        data-bs-target="#editModal" 
        onclick="loadDataForEditModal(${user.id})">Edit</button>
    </td>
        
    <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal" 
        onclick="deleteModalData(${user.id})">Delete</button>
    </td>
   
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}
getAdminPage();
getAdminUserPage();