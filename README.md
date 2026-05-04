RoomProject
===========

Overview
--------

RoomProject is a simple Android expense-tracking sample app that demonstrates modern Android architecture components and Room database usage. The app stores expenses locally, displays a dashboard with the total spent, and shows a history list where items can be added or deleted.

Highlights
---------
- **Platform**: Android (Kotlin)
- **Persistence**: `Room` (Entity, `@Dao`, `RoomDatabase`)
- **Architecture**: Repository pattern, `ViewModel` / `AndroidViewModel`
- **UI**: Fragments + `NavController` (Navigation component) with transition animations
- **Reactive data**: `kotlinx.coroutines.flow` exposed by DAO -> converted to LiveData via `asLiveData()`
- **Migrations**: `exportSchema = true` and an `AutoMigration` from version 1 → 2

Key Concepts & Where They Appear
--------------------------------

**Entity (Table)**: `Expense` — model for expense rows.
- File: [app/src/main/java/com/example/roomproject/data/local/Expense.kt](app/src/main/java/com/example/roomproject/data/local/Expense.kt)

**DAO (Data Access Object)**: `ExpenseDao` — insert, delete, query all, and get total.
- File: [app/src/main/java/com/example/roomproject/data/local/ExpenseDao.kt](app/src/main/java/com/example/roomproject/data/local/ExpenseDao.kt)

**Room Database**: `ExpenseDatabase` — singleton `RoomDatabase`, configured with `exportSchema = true` and an `AutoMigration` from v1 to v2.
- File: [app/src/main/java/com/example/roomproject/data/local/ExpenseDatabase.kt](app/src/main/java/com/example/roomproject/data/local/ExpenseDatabase.kt)
- Schema JSON files: [app/schemas/com.example.roomproject.data.local.ExpenseDatabase/1.json](app/schemas/com.example.roomproject.data.local.ExpenseDatabase/1.json) and [app/schemas/com.example.roomproject.data.local.ExpenseDatabase/2.json](app/schemas/com.example.roomproject.data.local.ExpenseDatabase/2.json)

Notes on Migrations
-------------------
- The project uses `autoMigrations = [ AutoMigration(from = 1, to = 2) ]` and `exportSchema = true`. This lets Room automatically generate the migration when the change between versions is supported by auto migration rules.
- The exported schema files (under `app/schemas/...`) provide a snapshot of the DB schema across versions — useful for audits, migrations, and verifying changes.

**Repository**: `ExpenseRepository` — abstracts data access so ViewModels don't depend on Room directly.
- File: [app/src/main/java/com/example/roomproject/data/repository/ExpenseRepository.kt](app/src/main/java/com/example/roomproject/data/repository/ExpenseRepository.kt)

**ViewModels**: `DashboardViewModel`, `HistoryViewModel`, `AddExpenseViewModel` — obtain repository via `ExpenseDatabase.getDatabase(application).expenseDao()` and expose LiveData for the UI.
- Files:
  - [app/src/main/java/com/example/roomproject/ui/dashboard/DashboardViewModel.kt](app/src/main/java/com/example/roomproject/ui/dashboard/DashboardViewModel.kt)
  - [app/src/main/java/com/example/roomproject/ui/history/HistoryViewModel.kt](app/src/main/java/com/example/roomproject/ui/history/HistoryViewModel.kt)
  - [app/src/main/java/com/example/roomproject/ui/add_expense/AddExpenseViewModel.kt](app/src/main/java/com/example/roomproject/ui/add_expense/AddExpenseViewModel.kt)

How data flows
-------------
- `ExpenseDao` returns `Flow<List<Expense>>` and `Flow<Double?>` for queries like total spent.
- The `Repository` exposes those flows.
- `ViewModel` converts flows to `LiveData` using `asLiveData()` so Fragments can `observe()` and update the UI.

UI and Navigation
-----------------
- The app uses Fragments and the Navigation Component. `MainActivity` hosts a `NavHostFragment` and wires the bottom navigation to the `NavController`.
  - File: [app/src/main/java/com/example/roomproject/MainActivity.kt](app/src/main/java/com/example/roomproject/MainActivity.kt)
- Navigation graph: [app/src/main/res/navigation/nav_graph.xml](app/src/main/res/navigation/nav_graph.xml) — actions to `addExpenseFragment` include enter/exit/pop animations (`app:enterAnim`, `app:exitAnim`, etc.). Animation resources are in `app/src/main/res/anim/` (e.g., `slide_in_right.xml`, `slide_out_left.xml`).

Fragments
---------
- `DashboardFragment`: shows total spent and floating action button to add expense.
  - File: [app/src/main/java/com/example/roomproject/ui/dashboard/DashboardFragment.kt](app/src/main/java/com/example/roomproject/ui/dashboard/DashboardFragment.kt)
- `HistoryFragment`: displays a `RecyclerView` backed by `ExpenseAdapter`, supports deleting items and navigating to add screen.
  - File: [app/src/main/java/com/example/roomproject/ui/history/HistoryFragment.kt](app/src/main/java/com/example/roomproject/ui/history/HistoryFragment.kt)
- `AddExpenseFragment`: simple form to create a new `Expense` and `navigateUp()` after save.
  - File: [app/src/main/java/com/example/roomproject/ui/add_expense/AddExpenseFragment.kt](app/src/main/java/com/example/roomproject/ui/add_expense/AddExpenseFragment.kt)

Adapter & RecyclerView
----------------------
- `ExpenseAdapter` (ListAdapter + DiffUtil) renders `Expense` items and handles delete clicks.
- File: [app/src/main/java/com/example/roomproject/ui/adapter/ExpenseAdapter.kt](app/src/main/java/com/example/roomproject/ui/adapter/ExpenseAdapter.kt)

Build & Run
---------
- Open the project in Android Studio (recommended) and run on an emulator or device.
- Gradle/Kotlin plugins and Room KSP are configured in the project build files (see `build.gradle.kts` files).

Notes & Next Steps
------------------
- If you later add schema changes that Room cannot auto-migrate, implement an explicit `Migration` and pass it into `Room.databaseBuilder(...).addMigrations(...)`.
- Consider adding unit/instrumented tests that exercise the DAO and migrations (Room provides helpers for in-memory DB testing).
- For large datasets, consider pagination with Paging 3.

Contact / Attribution
---------------------
This README was generated to summarize the project structure and key Android concepts used in the `RoomProject` sample.

Screenshots
-----------

Below are the key screenshots for the app. The repository does not include the image files yet — place the screenshots at the paths shown or upload them and I can add them for you.

- Dashboard (total spent):
<img width="459" height="961" alt="ss1" src="https://github.com/user-attachments/assets/b4b06f91-345a-46c2-accb-1654a64b444f" />

- History (expenses list):
<img width="472" height="953" alt="ss2" src="https://github.com/user-attachments/assets/0d667a66-0e45-4c09-8e0c-e1d1f4a5c7d8" />

- Database contents (Migrations):

V1:
<img width="1917" height="403" alt="room_project_v1" src="https://github.com/user-attachments/assets/73788977-3c22-4a13-849c-38bd011a4594" />
V2:
<img width="1919" height="410" alt="room_project_v2" src="https://github.com/user-attachments/assets/5785069c-4335-4108-b371-a1b135bb29b0" />
