use std::fs;

pub fn day03() {
    let contents =
        fs::read_to_string("..\\..\\input03").expect("Something went wrong reading the file");
    let map: Vec<&[u8]> = contents.split("\r\n").map(|x| x.as_bytes()).collect();
    let width = map[0].len();
    let height = map.len();
    let coords = vec![(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)];

    let mut ans = 1;
    for (dx, dy) in coords {
        let mut cpt = 0;
        for h in 0..height {
            let y = h * dy;
            if y < height {
                let x = (h * dx) % width;
                if map[y][x] == '#' as u8 {
                    cpt += 1;
                }
            }
        }
        println!("Answer for Right : {:?} down :{:?} = {:?}", dx, dy, cpt);
        ans *= cpt;
    }
    print!("Part 2 answer {:?}", ans)
}
