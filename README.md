# Steps to Set Up and Use Hilt to Consume & Render an API Endpoint

Follow these steps to integrate **Hilt** for dependency injection and fetch + display data from a REST API in your Jetpack Compose app.

### 1. Add Hilt Dependencies

Open your **module-level** `build.gradle.kts` (usually `app/build.gradle.kts`):

```gradle
plugins {
   // Add this line
   id("com.google.dagger.hilt.android") version "2.51" apply false
}

dependencies {
    // Hilt core - Add these lines
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")

    // Hilt + ViewModel - Add this line
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit + Gson (for API calls) - Add these lines
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}
```

### 2. Add Plugin to Apply the plugin in your Top Level build.gradle.kts:

Open your **project-level** `build.gradle.kts` (usually `<Project>/build.gradle.kts`):
```
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Add this line
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
```
### 3. Sync the Project

### 4. Create Application class with @HiltAndroidApplication

```
@HiltAndroidApp
class Application : Application()
```

### 5. Add Internet Connection and Application class to AndroidManifest.xml

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Add this line -- Add this line
    <uses-permission android:name="android.permission.INTERNET"/>

    <application
        android:name=".Application" <!-- Add Application class here>
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        <Rest of the code>.....
```

### 6. Create Module for Retrofit and Api Service NetworkModule.kt(di folder)
The **NetworkModule** is the single source of truth for creating and configuring Retrofit and its dependencies so the rest of the app can just **@Inject** the API service without knowing how it was built.

```

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}
```

### 7. Create repository

```
@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi
) {
    suspend fun getMovies(): List<Movie> = api.getMovies()
}
```


### 8. Create Sealed Class MovieState to keep track of Success, Loading, Error states

```
sealed class MovieState {
    object Loading: MovieState()
    data class Success(val movieData:List<AllMoviesItemModel>):MovieState()
    data class Error (val message:String): MovieState()
}
```

### 9. Use HiltViewModel to Fetch Data
```
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _moviesState = mutableStateOf<MovieState>(MovieState.Loading)
    val moviesState: State<MovieState> = _moviesState

    init {
       loadData()
    }

fun loadData(){
     viewModelScope.launch {
            try {
                val result = repository.getMovies()
                _moviesState.value = MovieState.Success(result)
            } catch (e: Exception) {
                _moviesState.value = MovieState.Error(e.message ?: "Unknown error")
            }
        }
}

```

### 10. Use data from viewmodel in your compose screen
```
@Composable
fun MovieScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val state by viewModel.movies

    when (state) {
        is MovieState.Loading -> CircularProgressIndicator()
        is MovieState.Success -> LazyColumn { ... }
        is MovieState.Error -> Text("Error: ${(state as MovieState.Error).message}")
    }
}
```


<br><br><br><br><br><br><br><br>
Video Proof

https://github.com/user-attachments/assets/6fad0668-898b-492a-bf74-bfeb5b14ce89










