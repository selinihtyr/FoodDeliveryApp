# FoodDeliveryApp

![App Icon](https://github.com/selinihtyr/FoodDeliveryApp/blob/58c34f22b51398cd9f0ce47f8d6bb77570efb405/app/src/main/res/mipmap-hdpi/ic_launcher.png)


Preview video: https://drive.google.com/file/d/1_x2EG8wm2MIV4IQqoycAzKq05oAkCRwp/view?usp=share_link

## Overview
The Food Delivery App is a comprehensive mobile application designed to facilitate users in browsing, selecting, and ordering their favorite meals. It features an intuitive user interface, efficient data management, and seamless integration with backend services.

## Features

- **Browse Foods**: View a wide range of food items right from the homepage.
- **Detailed View**: Inspect the details of each food item, including ingredients, price, and more.
- **Shopping Cart**: Add your favorite meals to the cart and manage your orders.
  
## Architecture & Design Patterns

### MVVM Architecture

The app employs the MVVM (Model-View-ViewModel) design pattern, ensuring a separation of concerns and making the codebase modular and maintainable.

### Dependency Injection

With the use of Hilt, the app effectively manages its dependencies, ensuring optimal performance and scalability.

### Repository Pattern

The data layer of the app utilizes the repository pattern, providing a clean and abstracted API for data access, regardless of the data source.

## Core Components

### Data Layer

- **Entities**: Data models that represent the structure of the app's data.
- **Data Sources**: Abstract the origin of data, ensuring flexibility and scalability.
- **Repositories**: Provide a clean API for data access to the UI layer.

### UI Layer

- **Adapters**: Connect the data to UI components, ensuring dynamic and responsive displays.
- **Fragments**: Represent different screens and functionalities of the app.
- **ViewModels**: Manage and store UI-related data, ensuring data persistence across configuration changes.

### Network Layer

The app uses Retrofit for its network operations, ensuring efficient and asynchronous API calls.

## Libraries & Tools

- **Hilt**: For dependency injection.
- **Retrofit**: For making network requests and handling API calls.
- **Kotlin Extensions**: To enhance and streamline the codebase.
