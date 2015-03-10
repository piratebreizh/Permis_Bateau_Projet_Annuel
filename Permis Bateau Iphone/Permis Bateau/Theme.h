//
//  Theme.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Theme : NSManagedObject

@property (nonatomic, retain) NSNumber * numero;
@property (nonatomic, retain) NSString * nom;

@end
