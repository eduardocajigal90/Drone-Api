INSERT INTO medication (name, weight, code, image_path, drone_id)
VALUES
    ( 'Medication1', 100.5, 'CODE1', 'image1.jpg', 1),
    ( 'Medication2', 200.0, 'CODE2', 'image2.jpg', 2),
    ( 'Medication3', 0.8, 'CODE3', 'image3.jpg', 3),
    ( 'Medication4', 3.5, 'CODE4', 'image4.jpg', 1),
    ( 'Medication5', 29.6, 'CODE5', 'image5.jpg', 1),
    ( 'Medication6', 15.8, 'CODE6', 'image6.jpg', 3),
    ( 'Medication7', 30.5, 'CODE7', 'image7.jpg', 2),
    ( 'Medication8', 20.6, 'CODE8', 'image8.jpg', 1),
    ( 'Medication9', 40.8, 'CODE9', 'image9.jpg', 3);

INSERT INTO drone ( serial_number, drone_model, weight_limit, battery_capacity, state)
VALUES
    ( 'DRONE001', 'Middleweight', 250.0, '100%', 'LOADING'),
    ( 'DRONE002', 'Lightweight', 300.0, '25%', 'IDLE'),
    ( 'DRONE003', 'Heavyweight', 400.0, '30%', 'LOADING'),
    ( 'DRONE004', 'Middleweight', 250.0, '100%', 'DELIVERED'),
    ( 'DRONE005', 'Cruiserweight', 300.0, '20%', 'DELIVERING'),
    ( 'DRONE006', 'Cruiserweight', 400.0, '50%', 'IDLE');