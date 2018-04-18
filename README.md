# Categories for Xamarin.Android

[Expandable RecyclerView](https://github.com/thoughtbot/expandable-recycler-view) by Amanda Hill is a great example of a library that enables categories with sub-items to exist in a RecyclerView.

Binding this project for Xamarin.Android proved to be challenging. As a result, a thin wrapper library  ubiquitously called *categories* was written that surfaces the library in an understandable fashion to Xamarin.Android's binding utility.

## Usage

[MainActivity.java](Expandable/app/src/main/java/com/disa/expandable/MainActivity.java) serves as a good entry point on how the *categories* library is interfaced in Java. Moreover, [MainActivity.cs](Categories/Categories.Test/MainActivity.cs) showcases the same example of categories library binded and interfaced in C#. Code between the two files has been kept as similar as possible.

## License

Expandable RecyclerView is Copyright (c) 2016 thoughtbot, inc. It is free software, and may be redistributed under the terms specified in the [LICENSE](https://github.com/thoughtbot/expandable-recycler-view/blob/master/LICENSE) file.

Categories for Xamarin.Android carries over Expandable RecyclerView's license.



