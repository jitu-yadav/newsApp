# 📰 NewsFlash — Modern News App (Android)

A clean, modular, **Jetpack Compose + Paging + Hilt + Room** powered News App that shows top headlines, lets users bookmark articles, search news, and read full details using a rich native UI or WebView fallback.

---

## 🚀 Features

### 🔍 News Feed
- Top headlines from a public news API
- Infinite scrolling using **Paging 3**
- **Swipe to refresh** (refreshes from page 1)
- Card previews with title, image, source, and time

### 🔎 Search News
- Debounced search (reduces API calls)
- Infinite scroll results
- Swipe-to-refresh support
- Bookmark directly from results

### 🔖 Bookmarks
- **Dedicated tab via Bottom Navigation**
- Save and remove articles
- Persistent offline bookmark storage using **Room**
- Accessible from any screen

### 📄 Article Detail
- Tap article to open a detailed page:
  - **Option A:** Native formatted content (if full content available)
  - **Option B:** WebView to original source URL
- Includes:
  - Share via Android Share Sheet
  - Bookmark toggle
  - Open in browser link

---

## 🧱 Architecture Overview

| Layer | Tech |
|-------|------|
| UI | Jetpack Compose, Bottom Navigation |
| Presentation | ViewModel, StateFlow |
| Domain | Use Cases, Models, Repository abstraction |
| Data | Repository impl, Retrofit API, Room DB, PagingSources |
| DI | Hilt |

Architecture Pattern: **Clean Architecture + MVVM**

---

## 🛠️ Tech Stack

| Category | Library |
|----------|---------|
| UI | Jetpack Compose, Material 3 |
| Navigation | Navigation Compose |
| DI | Hilt |
| Network | Retrofit + OkHttp + Moshi |
| Database | Room |
| Async | Kotlin Coroutines + Flow |
| Pagination | Paging 3 + Paging Compose |
| Images | Coil |

---
