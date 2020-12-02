use std::fs;

fn main() {
    let contents = fs::read_to_string("input01").expect("Something went wrong reading the file");

    let mut data: Vec<i32> = contents
        .split("\r\n")
        .map(|x| x.parse::<i32>().unwrap())
        .collect();

    data.sort();
    println!("== Part 1 ==");

    for x in (1..data.len()).rev() {
        let last: i32 = data[x];
        let diff = 2020 - last;
        let found = data.binary_search(&diff);
        match found {
            Ok(x) => {
                println!(
                    "{:?} {:?} {:?} {:?}",
                    data[x],
                    last,
                    data[x] + last,
                    data[x] * last
                );
                break;
            }
            Err(_) => (),
        }
    }

    println!("== Part 2 ==");

    for x in (0..data.len()).rev() {
        let first_number: i32 = data[x];
        let first_diff = 2020 - first_number;
        let found = data[0..x].binary_search(&first_diff);
        match found {
            Ok(_) => continue,
            Err(xx) => {
                for z in 0..xx {
                    let diff2 = first_diff - data[z];
                    let sub = &data[0..z + 1];
                    let found2 = sub.binary_search(&diff2);
                    match found2 {
                        Ok(y) => {
                            println!(
                                "{:?} {:?} {:?} {:?} {:?}",
                                first_number,
                                data[z],
                                data[y],
                                first_number + data[z] + data[y],
                                first_number * data[z] * data[y]
                            );
                            break;
                        }
                        Err(_) => (),
                    }
                }
            }
        }
    }
}
