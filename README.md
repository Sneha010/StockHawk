## Udacity Nanodegee APP (Project 3): StockHawk

The app finds stock prices and allows users to add stocks to track. User can watch the added stocks on widget.
It also graphs the added stock's value over time.

#### ScreenShots

![Stock list App](/screenshots/Stock_list_app.png?raw=true "Stock list App")
![Stock graph](/screenshots/stock_graph.png?raw=true "Stock graph")
![Stock list widget](/screenshots/stock_widget.png?raw=true "Stock list widget")

#### Tech

This App uses a number of open source projects to work properly:

* [Butterknife] - To bind the views with annotations.
* [MPAndroidChart] - To graph stock value over time.
* [Otto EventBus] - For smooth internal communication.
* Yahoo Stock API - To Fetch latest stock details.
* Material Design Support, RecyclerView Cards.


### Features
* Search ,add and view favorite stock.
* Monitor the added stocks in graphical format.
* View added stocks in a collection widget.

### Build and Run Requirements

* Oracle JDK 1.7
* Gradle 2.8
* Support Android 4.1 and Above (API 16)


### Tools used to develop
* Android Studio 1.4 and above

[Butterknife]: <http://jakewharton.github.io/butterknife/>
[MPAndroidChart]: <https://github.com/PhilJay/MPAndroidChart/>
[Otto EventBus]: <http://square.github.io/otto//>
