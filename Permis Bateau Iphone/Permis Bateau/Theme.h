//
//  Theme.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 26/04/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Theme : NSManagedObject

@property (nonatomic, retain) NSString * nom;
@property (nonatomic, retain) NSNumber * numero;
@property (nonatomic, retain) NSNumber * themeValider;

@end
