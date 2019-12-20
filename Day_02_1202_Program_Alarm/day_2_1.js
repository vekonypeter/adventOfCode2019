var fs = require('fs');

fs.readFile('input.txt', 'utf8', function (err, contents) {
    let nums = contents.split(',').map(num => Number.parseInt(num));
    let i = 0;
    let j = 1;
    console.log("INITIAL STATE");
    printNums(nums);

    while (nums[i] !== 99 && i < nums.length) {
        console.log("ITERATION: #" + j);

        if (nums[i] === 1) {
            nums[nums[i + 3]] = nums[nums[i + 1]] + nums[nums[i + 2]];
        }
        else if (nums[i] === 2) {
            nums[nums[i + 3]] = nums[nums[i + 1]] * nums[nums[i + 2]];
        }
        i += 4;

        printNums(nums);
        j++;
    }

    console.log(nums[0]);
});

function printNums(aNums) {
    for (let i = 0; i < aNums.length; i+=4) {
        console.log(aNums[i] + "-" + aNums[i+1] + "-" + aNums[i+2] + "-" + aNums[i+3]);
    }
}