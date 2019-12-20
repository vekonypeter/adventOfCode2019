var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    let wiresPaths = contents.split('\n').map(wirePaths => wirePaths.split(','));
    let wireAPaths = wiresPaths[0];
    let wireBPaths = wiresPaths[1];
    let wireACords = getWireCords(wireAPaths);
    let wireBCords = getWireCords(wireBPaths);

    console.log("GETTING INTERSECTION COORDINATES...");

    let intersections = wireACords.filter(cord => wireBCords.indexOf(cord) !== -1);

    console.log("DONE!");
    console.log("GETTING INTERSECTION DISTANCES...");

    intersections.forEach(interSectionCord => {
        let distanceForWireA = wireACords.indexOf(interSectionCord);
        let distanceForWireB = wireBCords.indexOf(interSectionCord);
        console.log(
            "Intersection: " + interSectionCord,
            "\n Distance for wire A: " + distanceForWireA,
            "\n Distance for wire B: " + distanceForWireB,
            "\n Combined distance: " + (distanceForWireA + distanceForWireB)
        );
    });

    console.log("DONE!");
});

function getWireCords(wirePaths) {
    return wirePaths.reduce((cords, path) => cords.concat(getPathCords(cords[cords.length - 1], path)), ["0>0"]);
}

function getPathCords(startCords, path) {
    let direction = path[0];
    let distance = Number.parseInt(path.substring(1));
    let startX = Number.parseInt(startCords.substring(0, startCords.indexOf('>')));
    let startY = Number.parseInt(startCords.substring(startCords.indexOf('>') + 1));
    let cords = [];
    for (let i = 1; i <= distance; i++) {
        switch (direction) {
            case 'R':
                cords.push((startX + i) + ">" + startY);
                break;
            case 'U':
                cords.push(startX + ">" + (startY + i));
                break;
            case 'L':
                cords.push((startX - i) + ">" + startY);
                break;
            case 'D':
                cords.push(startX + ">" + (startY - i));
                break;
        }
    }
    return cords;
}