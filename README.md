# Gemini_demo Assessment application for Gemini. Given a simple crypto coin to play with. I created amanagement platform for that coin.
The app contains a splash screen that was not in the requirements. I do not set the content to a layout. It is just a styled Activity that pops up instead of the primary color of the app when the app initially loads.
The requirements stated two views, so 1. LoginActivity and 2. MainActivity.
LoginActivity - Users log in with their names. User Data and all transactions are stored at login point.
MainActivity  - In MainActivity I have a view pager for users to navigate from their Profile (PROFILE) to the JobCoin Exchange View (TRANSACTIONS) and visa versa. 
PROFILE - This fragment contains user's balance, a graph and at the bottom a recycler view used as list for two tabs included in the fragment. Transactions and BuddyList. So users can view their own transactions and based on that list, be able to send to all people they have received coins from.
The application has a service hitting the server and updating user data every 15 seconds.
TRANSACTIONS - This fragment, serves as a JobCoin exchange board, consists of a recycler view list that shows all the transactions that occured for JobCoin over time
