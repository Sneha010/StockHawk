## Udacity Nanodegee App (Project 3): StockHawk

The app finds stock prices and allows users to add stocks to track. User can watch the added stocks on widget.
It also graphs the added stock's value over time.

#### ScreenShots

![Stock list App](/Screenshots/Stock_list_app.png?raw=true "Stock list App")
![Stock graph](/Screenshots/stock_graph.png?raw=true "Stock graph")
![Stock list widget](/Screenshots/stock_widget.png?raw=true "Stock list widget")

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

### License

```
Copyright 2016 Sneha Khadatare

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
