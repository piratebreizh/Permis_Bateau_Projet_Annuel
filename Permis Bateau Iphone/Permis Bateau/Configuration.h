//
//  Configuration.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 09/06/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Configuration : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * nom;
@property (nonatomic, retain) NSString * valeur;

@end
