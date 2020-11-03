const webpack = require('webpack');

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        SERVER_URL: JSON.stringify(process.env.SERVER_URL)
      }
    })
  ]
}
