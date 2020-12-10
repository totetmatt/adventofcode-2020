use std::fs;
pub fn day09() {
    let size = 25;
    let contents =
        fs::read_to_string("..\\..\\input09").expect("Something went wrong reading the file");
    let data: Vec<i64> = contents
        .split("\n")
        .map(|x| x.parse::<i64>().unwrap())
        .collect();
    let mut start = 0;
    loop {
        let q = &data[start..start + size];
        let t = data[start + size + 1];
        match q
            .iter()
            .position(|&x| q.iter().find(|&y| *y == t - x).is_some())
        {
            Some(_) => start += 1,
            _ => {
                println!("{:?}", t);
                break;
            }
        }
    }
}
