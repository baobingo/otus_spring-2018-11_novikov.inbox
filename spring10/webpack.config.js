var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
    entry: './src/front-end/index.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, 'target/classes/public/'),
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: path.resolve(__dirname, 'src/front-end/'),
        compress: true,
        port: 9000,
        open: true,
        historyApiFallback: true,
        disableHostCheck: true
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"],
                        plugins: ["@babel/plugin-proposal-class-properties"]
                    }
                }]
            },
            {
                test: /\.css$/,
                exclude: /(node_modules)/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                exclude: /(node_modules)/,
                loader: 'url-loader'
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'src/front-end/index.html')
        })
    ]
};