var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    let fuelRequirement = 0;
    contents.split('\n').forEach(moduleMass => {
        let fuelRequirementForModule = getFuelRequirement(moduleMass);
        let extraFuelRequirementForModule = getFuelRequirement(fuelRequirementForModule);
        while (extraFuelRequirementForModule > 0) {
            fuelRequirement += extraFuelRequirementForModule;
            extraFuelRequirementForModule = getFuelRequirement(extraFuelRequirementForModule);
        }
        fuelRequirement += fuelRequirementForModule;
    });
    console.log(fuelRequirement);
});

getFuelRequirement = function(mass) {
    return Math.floor(mass / 3) - 2;
}