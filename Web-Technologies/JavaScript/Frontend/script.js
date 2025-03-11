

// DOM Elements
const employeeForm = document.getElementById("employeeForm")
const employeeList = document.getElementById("employeeList")
const searchInput = document.getElementById("searchInput")
const formTitle = document.getElementById("formTitle")
const submitBtn = document.getElementById("submitBtn")
const cancelBtn = document.getElementById("cancelBtn")
const employeeIdField = document.getElementById("employeeId")

// Event Listeners
document.addEventListener("DOMContentLoaded", fetchAllEmployees)
employeeForm.addEventListener("submit", handleFormSubmit)
searchInput.addEventListener("input", searchEmployees)
cancelBtn.addEventListener("click", resetForm)


//
async function fetchAllEmployees() {
  const url = "http://localhost:8585/getAll";
  
  try {
    let res = await fetch(url);
    let employees = await res.json();
    console.log(employees);
    displayEmployees(employees);
  } catch (error) {
    console.error("Error fetching employees:", error);
  }
}

function displayEmployees(employees) {
  employeeList.innerHTML = ""

  if (employees.length === 0) {
    employeeList.innerHTML = '<tr><td colspan="7" style="text-align: center;">No employees found</td></tr>'
    return
  }

  employees.forEach((employee) => {
    const row = document.createElement("tr")
    row.innerHTML = `
      <td>${employee.eid}</td>
      <td>${employee.name}</td>
      <td>${employee.age}</td>
      <td>${employee.designation}</td>
      <td>${employee.salary}</td>
      <td>
        <button class="edit-btn" data-id="${employee.eid}" onclick="editEmployee(${employee.eid})">Edit</button>
        <button class="delete-btn" data-id="${employee.eid}">Delete</button>
      </td>
    `

    employeeList.appendChild(row)
  })
}


// Functions
function handleFormSubmit(e) {
  e.preventDefault()

  const name = document.getElementById("name").value
  const designation = document.getElementById("designation").value
  const age = document.getElementById("age").value
  const salary = document.getElementById("salary").value
  // const email = document.getElementById("email").value

  if (!name || !designation || !age || !salary) {
    alert("Please fill in all fields.")
    return
  }

  if (employeeIdField.value) {
    // Update existing employee
    updateEmployee(employeeIdField.value, name, designation, department, salary)
  } else {
    // Add new employee
    addEmployee(name, designation, age, salary)
  }

  resetForm()
  displayEmployees()
}
//Handle The validation for Name and Other Fields
function validateEmail(email) {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z]+  \.com$/; 
    const errorElement = document.getElementById("emailError");

    if (!re.test(email)) {
        errorElement.style.display = "block";  
        errorElement.textContent = "Please enter a valid email address (example@test.com).";
        return false;
    } else {
        errorElement.style.display = "none"; 
        return true;
    }
}



function addEmployee(name, designation, age, salary) {
  // Create new employee object with ID starting from 1
  const newEmployee = {
    "name":name,
    "age":age,
    "salary":salary,
    "designation":designation,
  }
  addEmployeeToDB(newEmployee);

  fetchAllEmployees();
}

async function addEmployeeToDB(newEmployee) {
  const url = "http://localhost:8585/add";
  
  try {
    let res = await fetch(url,{
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newEmployee),
    });
    let result = res.status();
    console.log(result);
  } catch (error) {
    console.error("Error fetching employees:", error);
  }
  
}

function updateEmployee(id, name, designation, department, salary, email) {
  // Find employee index
  const index = employees.findIndex((emp) => emp.id == id)

  if (index !== -1) {
    // Update employee data
    employees[index] = {
      ...employees[index],
      name,
      designation,
      department,
      salary,
      email,
    }

    // Save to localStorage
    saveEmployees()
  }
}

function deleteEmployee(id) {
  if (confirm("Are you sure you want to delete this employee?")) {
    // Filter out the employee with the given id
    employees = employees.filter((emp) => emp.id != id)

    // Reassign IDs to maintain sequential order starting from 1
    employees = employees.map((emp, index) => ({
      ...emp,
      id: index + 1,
    }))

    // Save to localStorage
    saveEmployees()

    // Refresh display
    displayEmployees()
  }
}


async function editEmployee(id) {
  console.log("EDIT CALLED"+id);
  const url = "http://localhost:8585/getById/" + id;
  
  // Find employee
  try {
    let res = await fetch(url);
    let employee = await res.json(); // Wait for the employee data
    console.log(employee);

    if (employee) {
      // Populate form fields
      // document.getElementById("employeeIdField").value = employee.eid; 
      document.getElementById("name").value = employee.name;
      document.getElementById("designation").value = employee.designation;
      document.getElementById("age").value = employee.age;
      document.getElementById("salary").value = employee.salary;

      // Update UI elements
      document.getElementById("formTitle").textContent = "Edit Employee";
      document.getElementById("submitBtn").textContent = "Update Employee";

      // Show cancel button
      document.getElementById("cancelBtn").classList.remove("hidden");

      // Scroll to form smoothly
      document.querySelector(".form-container").scrollIntoView({ behavior: "smooth" });
    }
  } catch (error) {
    console.error("Error fetching employee:", error);
  }
}

function resetForm() {
  // Clear form
  employeeForm.reset()
  employeeIdField.value = ""

  // Reset form title and button text
  formTitle.textContent = "Add Employee"
  submitBtn.textContent = "Add Employee"

  // Hide cancel button
  cancelBtn.classList.add("hidden")
}

function displayEmployees(employeesToDisplay = employees) {
  // Clear current list
  employeeList.innerHTML = ""

  // Check if there are employees to display
  if (employeesToDisplay.length === 0) {
    employeeList.innerHTML = '<tr><td colspan="7" style="text-align: center;">No employees found</td></tr>'
    return
  }



  

  // Add each employee to the table
  employeesToDisplay.forEach((employee) => {
    const row = document.createElement("tr")

    row.innerHTML = `
            <td>${employee.eid}</td>
            <td>${employee.name}</td>
            <td>${employee.age}</td>

            <td>${employee.designation}</td>
            <td>$${employee.salary}</td>
            
            <td>
                <button class="action-btn edit-btn" onclick="editEmployee(${employee.eid})">Edit</button>
                <button class="action-btn delete-btn" onclick="deleteEmployee(${employee.eid})">Delete</button>
            </td>
        `

    employeeList.appendChild(row)
  })
}

function searchEmployees() {
  const searchTerm = searchInput.value.toLowerCase()

  if (searchTerm.trim() === "") {
    displayEmployees()
    return
  }

  let employees = fetchAllEmployees();
  // Filter employees based on search term
  const filteredEmployees = employees.filter((employee) => {
    return (
      employee.name.toLowerCase().includes(searchTerm) ||
      employee.designation.toLowerCase().includes(searchTerm) ||
      employee.age.toString( ).includes(searchTerm) ||
      employee.eid.toString().includes(searchTerm)
    )
  })

  // Display filtered results
  displayEmployees(filteredEmployees)
}


