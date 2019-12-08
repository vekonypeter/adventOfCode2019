console.log("STARTED!");

let count = 0;
for (let i = 367479; i <= 893698; i++) {
    let numString = i.toString();
    if (numString.match(/^(?=\d{6}$)0*1*2*3*4*5*6*7*8*9*$/)) {
        // if (numString.match( /(.)\1{1}/) && !numString.match( /(.)\1{2,}/)) {
        if (numString.match(/(.)(?<!\1\1)\1(?!\1)/)) {
            console.log(numString + " MATCHES!");
            count++;
        }
    }
}

console.log("DONE! FOUND MATCHES: " + count);