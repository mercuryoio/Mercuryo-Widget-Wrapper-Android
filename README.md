# Mercuryo-Widget-Wrapper-Android
Mercuryo Widget Wrapper is a lightweight library for developers of apps who embed Mercuryo Widget into their mobile UX.
Solely purpose of the library is to provide an easy way to initialize WebView passing correct parameters with all needed permissions.
More info here: https://mercuryo.io/widget/ and here https://help.mercuryo.io/en/articles/4519473-mercuryo-widget-faq.
## Requirements

- Android 4.1+

## Communication

- If you need to **find or understand an API**, check [our documentation](https://https://mercuryo.io/widget/).
- If you need **more information**, check [our help page](https://help.mercuryo.io/en/articles/4519473-mercuryo-widget-faq).
- If you **found a bug**, open an issue. The more detail the better!
- If you **want to contribute**, submit a pull request.


## Usage

### Widget Configuration activity delegate

For the first, you should create a configuration for your widget. You can choose one of the standard environment `production` or `sandbox`

```kotlin
import MRCRWidget
class MainActivity: MercuryoWebViewActivity(val viewID: Int){

}

```

### Supporting Widget UI Delegate

## License

Mercuryo Widget is released under the MIT license. [See LICENSE](https://github.com/mercuryoio/Mercuryo-Widget-Wrapper-iOS/blob/master/LICENSE.md) for details.
