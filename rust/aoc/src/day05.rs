use std::fs;
pub fn day05() {
    fn c_to_i(s: &str, zero: char, one: char) -> i32 {
        isize::from_str_radix(&s.replace(one, "1").replace(zero, "0"), 2).unwrap() as i32
    }
    fn seat_id(s: &str) -> i32 {
        let rr = c_to_i(&s[0..7], 'F', 'B');
        let ss = c_to_i(&s[7..10], 'L', 'R');
        rr * 8 + ss
    }

    let contents =
        fs::read_to_string("..\\..\\input05").expect("Something went wrong reading the file");
    let tickets: Vec<&str> = contents.split("\n").collect();
    let mut toto: Vec<i32> = tickets.iter().map(|x| seat_id(x)).collect();
    println!("{:?}", toto.iter().max());
    toto.sort();
    let found = toto
        .windows(2)
        .take_while(|x| x[0] + 1 == x[1])
        .last()
        .unwrap()
        .last()
        .unwrap();
    println!("{:?}", found + 1);
}
