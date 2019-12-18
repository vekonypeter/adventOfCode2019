var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    const WIDTH = 25;
    const HEIGHT = 6;
    const LAYER_LENGTH = WIDTH * HEIGHT;

    var layers = [];
    var searchedLayer = null;
    for (let i = 0; i < contents.length; i += LAYER_LENGTH) {
        let newLayerDigits = contents.substring(i, i + LAYER_LENGTH).split('').map(s => Number.parseInt(s));
        let numberOfZeros = newLayerDigits.filter(n => n === 0).length;
        let newLayer = { digits: newLayerDigits, numberOfZeros: numberOfZeros }
        layers.push(newLayer);

        if (searchedLayer === null || numberOfZeros < searchedLayer.numberOfZeros) {
            searchedLayer = newLayer;
        }
    }

    console.log("INPUT LENGTH", contents.length);
    console.log("ONE LAYER'S LENGTH", LAYER_LENGTH);
    console.log("NUMBER OF LAYERS", layers.length);
    console.log("LAYER WITH MOST ZEROS", layers.indexOf(searchedLayer));
    console.log("MOST ZEROS", searchedLayer.numberOfZeros);
    let ones = searchedLayer.digits.filter(d => d === 1).length;
    let twos = searchedLayer.digits.filter(d => d === 2).length;
    console.log("ONEs IN LAYER WITH MOST ZEROS", ones);
    console.log("TWOs IN LAYER WITH MOST ZEROS", twos);
    console.log("PART 1 RESULT", ones * twos);

});