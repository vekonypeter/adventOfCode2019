var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    let fuelRequirement = 0;
    contents.split('\n').forEach(line => {
        let fuelRequirementForModule = Math.floor(line / 3) - 2;
        fuelRequirement += fuelRequirementForModule;
    });
    console.log(fuelRequirement);
});