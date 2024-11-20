module.exports = {
  // Other webpack configuration settings...
  devServer: {
    client: {
      webSocketURL: {
        protocol: 'wss'
      }
    }
  }
};
