 const userUrl = "/api/auth";

const authUser = fetch(userUrl).then(response => response.json())
 authUser.then(user => {
         let roles = ''
         user.roles.forEach(role => {
             roles += ' '
             roles += role.name.toString()
         })
        document.getElementById("navbar-username").innerHTML = user.username
        document.getElementById("navbar-roles").innerHTML = roles
     }
 )

async function getAdminUserPage() {
    let page = await fetch(userUrl);

    if(page.ok) {
        let user = await page.json();
        getInformationAboutUser(user);
    } else {
        alert(`Error, ${page.status}`)
    }
}
function  getInformationAboutUser(user) {
    const tableBody = document.getElementById('adminUserTableBody');
    let dataHtml;
    let roles = [];
    console.log('userData', JSON.stringify(user))
    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
        )
    }
    dataHtml =
`<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.name}</td>
    <td>${user.surname}</td>
    <td>${user.age}</td>
    <td>${roles}</td>   
</tr>`

    tableBody.innerHTML = dataHtml;
}
 getAdminUserPage();
