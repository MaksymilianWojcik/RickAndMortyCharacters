# RickAndMortyCharacters
Rick and Morty Wiki app project with MVVM pattern and architecture components (Local cache with room)

# About

This is the MVVM implementation of my [Rick and Morty Characters app](https://github.com/MaksymilianWojcik/RickAndMortyWikiOfficial) that was available in the store. It's just a simple app showing all characters from Rick and Morty series.
The app is based on [Rick and Morty API (ShlaAPI)](https://rickandmortyapi.com/) created by Axel Fuhrmann and based on the television show [Rick and Morty](https://www.adultswim.com/videos/rick-and-morty).
All data received from API is stored in the local cache implemented with Room (and with Single Source of Truth principle).
The app supports multiple screen sizes and translations for English, Polish and German (except the data from API)


# Dependencies
- Architecture components (Room, LiveData, ViewModel)
- Retrofit 2 with okhttp and GSON converter
- Glide
- ButterKnife
- Timber
- CardView + RecyclerView (?)

# TODOS
- Refactor with Dagger 2
- Add glide preloaders for caching
- Add categories and other screens: Episodes (with links), Locations
- Add navigating to episode details from character details
- Improve UI + vectors
- Favorite characters <3
- Random Rick and Morty quotes retrieved from firebase database
- Add tests
