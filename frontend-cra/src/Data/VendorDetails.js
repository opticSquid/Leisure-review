export const data = [
  {
    placeName: "SB Resort",
    address: "Arambagh",
    description: "5 star Hotel",
    rating: 4.3,
    placeType: "hotel",
    details: {
      numberOfRooms: 2,
      roomCategories: "normal, delux, premium",
      pricings: {
        normal: 22.0,
        delux: 32.0,
        premium: 42.0,
      },
      isVegAvailable: true,
      offDays: "none",
      otherServices: ["transport", "room service"],
    },
  },
  {
    placeName: "SB Park",
    address: "Arambagh",
    description: "5 star Park",
    rating: 4.3,
    placeType: "park",
    details: {
      isChildrensPark: false,
      rides: "boating, water-ride, zooba-doo",
      isWaterparkAvailable: true,
      minimumAgeForRides: {
        boating: 10,
        "water-ride": 7,
        "zooba-doo": 15,
      },
      entryFee: 600,
      ridefeesAndComboPacks: {
        boating: 100.0,
        "water-ride": 200.0,
        "zooba-doo": 300.0,
      },
      otherDetails: "striking car available",
    },
  },
  {
    placeName: "SB Mall",
    address: "Arambagh",
    description: "5 star Mall",
    rating: 4.3,
    placeType: "mall",
    details: {
      numberOfShops: 50,
      floorArea: 1000.0,
      numberOfParkingSpaces: 250,
      numberofRestaurants: 100,
    },
  },
];
