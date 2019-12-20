var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");

const IMAGE_WIDTH = 25;
const IMAGE_HEIGHT = 6;
const LAYER_LENGTH = IMAGE_WIDTH * IMAGE_HEIGHT;

const ZOOM = 10;

initCanvas();

fetch("/input.txt", {
    method: "GET"
})
    .then(response => response.text())
    .then(input => {

        var layers = [];
        var searchedLayer = null;
        for (let i = 0; i < input.length; i += LAYER_LENGTH) {
            let newLayerDigits = input.substring(i, i + LAYER_LENGTH).split('').map(s => Number.parseInt(s));
            let numberOfZeros = newLayerDigits.filter(n => n === 0).length;
            let newLayer = { digits: newLayerDigits, numberOfZeros: numberOfZeros }
            layers.push(newLayer);

            if (searchedLayer === null || numberOfZeros < searchedLayer.numberOfZeros) {
                searchedLayer = newLayer;
            }
        }

        console.log("INPUT LENGTH", input.length);
        console.log("ONE LAYER'S LENGTH", LAYER_LENGTH);
        console.log("NUMBER OF LAYERS", layers.length);
        console.log("LAYER WITH MOST ZEROS", layers.indexOf(searchedLayer));
        console.log("MOST ZEROS", searchedLayer.numberOfZeros);
        let ones = searchedLayer.digits.filter(d => d === 1).length;
        let twos = searchedLayer.digits.filter(d => d === 2).length;
        console.log("ONEs IN LAYER WITH MOST ZEROS", ones);
        console.log("TWOs IN LAYER WITH MOST ZEROS", twos);
        console.log("PART 1 RESULT", ones * twos);

        layers.reverse().forEach(drawLayer);
    });

function initCanvas() {
    canvas.setAttribute("width", IMAGE_WIDTH * ZOOM);
    canvas.setAttribute("height", IMAGE_HEIGHT * ZOOM);
    context.fillStyle = "red";
    context.fillRect(0, 0, IMAGE_WIDTH * ZOOM, IMAGE_HEIGHT * ZOOM);
}

function drawLayer(layer) {
    for (let y = 0; y < IMAGE_HEIGHT; y++) {
        for (let x = 0; x < IMAGE_WIDTH; x++) {
            switch (layer.digits[x + y * IMAGE_WIDTH]) {
                case 0:
                    context.fillStyle = "black";
                    break;
                case 1:
                    context.fillStyle = "white";
                    break;
                case 2:
                    context.fillStyle = "transparent";
                    break;
            }
            context.fillRect(x * ZOOM, y * ZOOM, ZOOM, ZOOM);
        }
    }
}
