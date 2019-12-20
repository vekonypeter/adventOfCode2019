var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    let originalNums = contents.split(',').map(num => Number.parseInt(num));

    for (let noun = 0; noun <= 99; noun++) {
        for (let verb = 0; verb <= 99; verb++) {
            console.log("NOUN: " + noun, "VERB: " + verb);
            let nums = [...originalNums];
            nums[1] = noun;
            nums[2] = verb;
            let result = executeProgram(nums);
            if (result === 19690720) {
                console.log("FOUND IT!");
                return;
            }
        }
    }

});

function executeProgram(nums) {
    let i = 0;
    while (nums[i] !== 99 && i < nums.length) {
        if (nums[i] === 1) {
            nums[nums[i + 3]] = nums[nums[i + 1]] + nums[nums[i + 2]];
        }
        else if (nums[i] === 2) {
            nums[nums[i + 3]] = nums[nums[i + 1]] * nums[nums[i + 2]];
        }
        i += 4;
    }
    return nums[0];
}