// Initialize employees array from localStorage or empty array if none exists
let employees = JSON.parse(localStorage.getItem("employees")) || []

// DOM Elements
const employeeForm = document.getElementById("employeeForm")
const employeeList = document.getElementById("employeeList")
const searchInput = document.getElementById("searchInput")
const formTitle = document.getElementById("formTitle")
const submitBtn = document.getElementById("submitBtn")
const cancelBtn = document.getElementById("cancelBtn")
const employeeIdField = document.getElementById("employeeId")

// Event Listeners
document.addEventListener("DOMContentLoaded", displayEmployees)
employeeForm.addEventListener("submit", handleFormSubmit)
searchInput.addEventListener("input", searchEmployees)
cancelBtn.addEventListener("click", resetForm)

// Functions
function handleFormSubmit(e) {
  e.preventDefault()

  const name = document.getElementById("name").value
  const position = document.getElementById("position").value
  const department = document.getElementById("department").value
  const salary = document.getElementById("salary").value
  const email = document.getElementById("email").value

  if (!name || !position || !department || !salary || !email) {
    alert("Please fill in all fields.")
    return
  }
  if(validateEmail(email) == false){
    alert("Please enter a valid email address.")
    return
  }

  if (employeeIdField.value) {
    // Update existing employee
    updateEmployee(employeeIdField.value, name, position, department, salary, email)
  } else {
    // Add new employee
    addEmployee(name, position, department, salary, email)
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



function addEmployee(name, position, department, salary, email) {
  // Create new employee object with ID starting from 1
  const newEmployee = {
    id: employees.length + 1,
    name,
    position,
    department,
    salary,
    email,
  }

  // Add to employees array
  employees.push(newEmployee)

  // Save to localStorage
  saveEmployees()
}

function updateEmployee(id, name, position, department, salary, email) {
  // Find employee index
  const index = employees.findIndex((emp) => emp.id == id)

  if (index !== -1) {
    // Update employee data
    employees[index] = {
      ...employees[index],
      name,
      position,
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

function editEmployee(id) {
  // Find employee
  const employee = employees.find((emp) => emp.id == id)

  if (employee) {
    // Populate form
    employeeIdField.value = employee.id
    document.getElementById("name").value = employee.name
    document.getElementById("position").value = employee.position
    document.getElementById("department").value = employee.department
    document.getElementById("salary").value = employee.salary
    document.getElementById("email").value = employee.email

    // Change form title and button text
    formTitle.textContent = "Edit Employee"
    submitBtn.textContent = "Update Employee"

    // Show cancel button
    cancelBtn.classList.remove("hidden")

    // Scroll to form
    document.querySelector(".form-container").scrollIntoView({ behavior: "smooth" })
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
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.position}</td>
            <td>${employee.department}</td>
            <td>$${employee.salary}</td>
            <td>${employee.email}</td>
            <td>
                <button class="action-btn edit-btn" onclick="editEmployee(${employee.id})">Edit</button>
                <button class="action-btn delete-btn" onclick="deleteEmployee(${employee.id})">Delete</button>
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

  // Filter employees based on search term
  const filteredEmployees = employees.filter((employee) => {
    return (
      employee.name.toLowerCase().includes(searchTerm) ||
      employee.position.toLowerCase().includes(searchTerm) ||
      employee.department.toLowerCase().includes(searchTerm) ||
      employee.email.toLowerCase().includes(searchTerm) ||
      employee.id.toString().includes(searchTerm)
    )
  })

  // Display filtered results
  displayEmployees(filteredEmployees)
}

function saveEmployees() {
  localStorage.setItem("employees", JSON.stringify(employees))
}

