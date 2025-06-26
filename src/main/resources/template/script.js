
const form = document.getElementById('userForm');
const userList = document.getElementById('userList');

let users = [];
let editIndex = -1;

form.addEventListener('submit', function(e) {
e.preventDefault();

const nome = form.nome.value.trim();
const email = form.email.value.trim();
const telefone = form.telefone.value.trim();

if (!nome || !email || !telefone) return;

if (editIndex === -1) {
users.push({ nome, email, telefone });
} else {
users[editIndex] = { nome, email, telefone };
editIndex = -1;
form.querySelector('button').textContent = 'Adicionar';
}

form.reset();
renderList();
});

function renderList() {
userList.innerHTML = '';

users.forEach((user, index) => {
const li = document.createElement('li');

const userInfo = document.createElement('div');
userInfo.innerHTML = `
<p><strong>Nome:</strong> ${user.nome}</p>
<p><strong>Email:</strong> ${user.email}</p>
<p><strong>Telefone:</strong> ${user.telefone}</p>
`;

const actions = document.createElement('div');
actions.classList.add('actions');

const editBtn = document.createElement('button');
editBtn.textContent = 'Editar';
editBtn.onclick = () => editUser(index);

const deleteBtn = document.createElement('button');
deleteBtn.textContent = 'Excluir';
deleteBtn.onclick = () => deleteUser(index);

actions.appendChild(editBtn);
actions.appendChild(deleteBtn);

li.appendChild(userInfo);
li.appendChild(actions);

userList.appendChild(li);
});
}

function editUser(index) {
const user = users[index];
form.nome.value = user.nome;
form.email.value = user.email;
form.telefone.value = user.telefone;
editIndex = index;
form.querySelector('button').textContent = 'Atualizar';
}

function deleteUser(index) {
if (confirm('Deseja realmente excluir este usuário?')) {
users.splice(index, 1);
renderList();
}
}

renderList();
