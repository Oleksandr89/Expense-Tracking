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
  <img src="https://github.com/user-attachments/assets/688d5800-d90d-47f0-8d44-ea3793f00200" alt="Image 1" width="30%" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/b92efeb1-5b6b-4b29-87fc-70821d494219" alt="Image 2" width="30%" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/9415c56e-0fbf-4fd0-9d84-55999b564557" alt="Image 3" width="30%" />
</p>
