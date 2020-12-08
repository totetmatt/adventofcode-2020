use std::collections::HashMap;
use std::fs;
pub fn day07() {
    let mut graph: HashMap<&str, Vec<&str>> = HashMap::new();
    let contents =
        fs::read_to_string("..\\..\\input07").expect("Something went wrong reading the file");
    let forms = contents.split("\r\n");
    for a in forms {
        let sr = a.replace("bags", "");
        let s1 = sr.split("contain").collect::<Vec<&str>>();
        let k = s1[0].trim();
        let vr = s1[1].replace(".", "");
        let v = vr.split(",").map(|x| x.trim()).collect::<Vec<&str>>();
        println!("{:?} {:?}", k, v);
    }
}
