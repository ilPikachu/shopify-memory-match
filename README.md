# shopify-memory-match

This project is created for the Shopify Android internship challenge (Fall 2020).

The architecture of the app follows the MVVM pattern with repository. It utilizes modern Android libraries and Shopify Polaris design system for styling.

### Libraries
- Navigation (Single activity pattern)
- ViewModel
- Retrofit (Network)
- Moshi (JSON parser)
- Glide (Image caching)
- Preference (Settings)
- Databinding
- LiveData
- Room (Saving ProductImage response and UserScores)
- Coroutines (Async network and DB operations)
- Material Design Component

### Design with Shopify Polaris
- Color schemes in the app are selected from [Polaris color palette](https://polaris.shopify.com/design/colors).
- Dimens follows the [Polaris typography](https://polaris.shopify.com/design/typography#navigation).
- Font family follows [Polaris typography](https://polaris.shopify.com/design/typography#navigation). 
    > "Android devices will display Roboto."
- Created Shopify styles for ease of development. 
- Icons are from [Polaris icon explorer](https://polaris-icons.shopify.com/) or [Material design](https://material.io/resources/icons/?style=baseline) for the ones I couldn't find.

### Required Screenshots
No match | Some match | All match & User wins
--- | --- | ---
![No match](/screenshots/no_match.png) | ![Some match](/screenshots/some_matches.png) | ![All match & User wins](/screenshots/user_wins.png)

### Requirements and Bonuses
Requirements | Completion
--- | ---
The user should have to find a minimum of 10 pairs to win. | Done
Keep track of how many pairs the user has found. | Done
When the user wins, display a message to let them know! | Done
Make sure it compiles successfully. | Done

Bonuses | Completion
--- | ---
Make the game configurable to match 3 or 4 of the same products instead of 2. | Done
Make the grid size configurable. (Match more than 10 sets of the same product). | Done
Build a slick screen that keeps track of the user’s score. | Done
Make a button that shuffles the game. | Done
Feel free to make the app beautiful and add anything else you think would be cool! | Polaris design

### Additional Screenshots
Home | Configurable game | Score
--- | --- | ---
![No match](/screenshots/home.png) | ![Some match](/screenshots/configurable_game.png) | ![All match & User wins](/screenshots/scores.png)

Settings | Settings pairs selection | No score
--- | --- | ---
![No match](/screenshots/settings.png) | ![Some match](/screenshots/settings_pair_selection.png) | ![All match & User wins](/screenshots/no_scores.png)


### Easter egg

> (=ↀωↀ=)✧
