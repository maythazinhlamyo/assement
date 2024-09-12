import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
 import { provideRouter } from '@angular/router';
 import { AppRoutingModule } from './app-routing.module';
//import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true })]
};

// export const AppConfig = {
//   apiBaseUrl: 'http://localhost:8080/api', // Base URL for API calls
//   auth: {
//     basicAuth: true,  // Set to true if using Basic Auth
//   },
//   pagination: {
//     defaultPageSize: 10,  // Default number of items per page
//   },
//   search: {
//     enableSearch: true,  // Enable or disable search functionality
//   },
//   headers: {
//     contentType: 'application/json',  // Default Content-Type header for requests
//   }
// };
