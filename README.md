<b><h3>Expense Tracking</h3></b>
___

This project is a sample personal finance app that using Jetpack Compose and clean architecture principles. The app allows users to track income and expenses with a modular and testable architecture.

In this project you'll find:

**User Interface built with Jetpack Compose**  
All screens and UI components are implemented using the declarative UI toolkit from Jetpack Compose.

**Single-activity architecture using Navigation Compose**  
Navigation between screens is handled with the Navigation Compose library.

**Modular architecture**  
The project is split into core modules such as:

- core: Shared base classes and utilities.

- transactions: Feature module for handling expenses and incomes.

- balance: Handles current balance calculation and logic.

**Reactive UIs with Kotlin Flow and coroutines**  
State flows and suspend functions are used for async operations and UI state updates.

**Room database with Paging 3**  
Data persistence is implemented with Room, and transaction lists are paginated using Paging 3.

**ViewModel per feature**  
Each screen or feature has its own ViewModel to separate presentation logic.

**Dependency injection with Hilt**  
All modules use Hilt for managing dependencies across the app.

**Testable and scalable codebase**  
Architecture is designed to allow for easy testing and future scaling.  

  
<b><h3>Screenshots</h3></b>
___

<p align="center">
  <img src="https://github.com/user-attachments/assets/336ae7ed-fea2-4620-a08c-c7aeab95dcb5" alt="Image 1" width="30%" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/4ee81bd0-5bc0-4680-9151-887bddb54d84" alt="Image 2" width="30%" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/3252b47f-77b9-49b9-a78a-83903a1d39c6" alt="Image 3" width="30%" />
</p>
