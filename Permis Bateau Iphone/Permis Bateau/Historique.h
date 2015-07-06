//
//  Historique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 25/06/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Serie;

@interface Historique : NSManagedObject

@property (nonatomic, retain) NSDate * date;
@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * resultat;
@property (nonatomic, retain) NSNumber * blancReussit;
@property (nonatomic, retain) Serie *serie;

@end
